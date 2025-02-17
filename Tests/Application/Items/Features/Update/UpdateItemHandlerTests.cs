﻿using Application.Items.Features.Update;
using Application.Items.Interfaces;
using Application.Items.Shared;
using Application.Shared.Results;
using Domain.Entities;
using Moq;
using Tests.Application.Items.Shared;

namespace Tests.Application.Items.Features.Update;

public class UpdateItemHandlerTests : ItemHandlerTests<UpdateItemHandler, IUpdateItemValidator, UpdateItemRequest>
{
    [Fact]
    public async Task ItShouldFailWhenRequestIsNotValid()
    {
        //Arrange
        UpdateItemRequest invalidRequest = new(Guid.Empty, string.Empty);

        // Act and Arrange
        await ItShouldFailWhenRequestIsNotValidBase(invalidRequest);

        VerifyFindAsync(Times.Never);
        VerifyUpdateExecution(Times.Never);
    }

    [Fact]
    public async Task ItShouldUpdateItemWhenRequestIsValid()
    {
        // Arrange
        Item item = new()
        {
            Id = Guid.NewGuid(),
            Title = "Item 001",
        };
        UpdateItemRequest request = new(item.Id, "Item 002");

        CancellationToken cancellationToken = CancellationToken.None;

        SetupFindAsync(item);
        SetupValidatorResult();

        // Act
        Result<ItemResponse?> result = await Handler.HandleAsync(request, cancellationToken);

        // Assert
        Assert.NotNull(result);
        Assert.True(result.IsSuccess);
        Assert.Empty(result.Errors);
        Assert.NotNull(result.Value);
        ItemResponse response = Assert.IsType<ItemResponse>(result.Value);
        Assert.Equal(request.Id, response.Id);
        Assert.Equal(request.Title, response.Title);

        VerifyValidatorExecution(Times.Once);
        VerifyFindAsync(Times.Once);
        VerifyUpdateExecution(Times.Once);
        VerifyCommitExecution(Times.Once);

        RepositoryMock
            .Verify(
                p => p.FindAsync(It.IsAny<Guid>(), It.IsAny<CancellationToken>()),
                Times.Once
            );
    }

    private void SetupFindAsync(Item item)
    {
        RepositoryMock
            .Setup(p => p.FindAsync(It.IsAny<Guid>(), It.IsAny<CancellationToken>()))
            .ReturnsAsync(item);
    }

    private void VerifyFindAsync(Func<Times> times)
    {
        RepositoryMock
            .Verify(
                p => p.FindAsync(It.IsAny<Guid>(), It.IsAny<CancellationToken>())
                , times
            );
    }

    private void VerifyUpdateExecution(Func<Times> times)
    {
        RepositoryMock.Verify(p => p.Update(It.IsAny<Item>()), times);
    }
}