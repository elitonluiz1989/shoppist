using Application.Items.Features.Create;
using Application.Items.Interfaces;
using Application.Items.Shared;
using Application.Shared.Results;
using Domain.Entities;
using Moq;
using Tests.Application.Items.Shared;

namespace Tests.Application.Items.Features.Create;

public class CreateItemHandlerTests : ItemHandlerTests<CreateItemHandler, ICreateItemValidator, ItemRequest>
{
    [Fact]
    public async Task ShouldItFailWhenRequestIsNotValid()
    {
        // Arrange
        ItemRequest request = new(string.Empty);

        // Act and Assert
        await ShouldItFailWhenRequestIsNotValidBase(request);
        VerifyCreateExecution(Times.Never);
    }

    [Fact]
    public async Task ShouldItCreateItemWhenRequestIsValid()
    {
        // Arrange
        ItemRequest request = new("Item 001");
        CancellationToken cancellationToken = CancellationToken.None;

        SetupValidatorResult();

        // Act
        Result<ItemResponse?> result = await Handler.HandleAsync(request, cancellationToken);

        // Assert
        Assert.NotNull(result);
        Assert.True(result.IsSuccess);
        Assert.Empty(result.Errors);
        Assert.NotNull(result.Value);
        ItemResponse response = Assert.IsType<ItemResponse>(result.Value);
        Assert.Equal(request.Title, response.Title);

        VerifyValidatorExecution(Times.Once);
        VerifyCreateExecution(Times.Once);
        VerifyCommitExecution(Times.Once);
    }

    private void VerifyCreateExecution(Func<Times> times)
    {
        RepositoryMock.Verify(p => p.Create(It.IsAny<Item>()), times);
    }
}