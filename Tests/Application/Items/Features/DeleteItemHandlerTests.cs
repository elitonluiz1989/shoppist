using Application.Items.Features.Delete;
using Application.Shared.Results;
using Application.Shared.Validators;
using Domain.Entities;
using Domain.Interfaces;
using Domain.Interfaces.Base;
using Moq;
using Moq.AutoMock;
using Tests.Helpers;

namespace Tests.Application.Items.Features;

public class DeleteItemHandlerTests
{
    private readonly DeleteItemHandler _handler;
    private readonly Mock<IItemRepository> _repositoryMock;
    private readonly Mock<IUnitOfWork> _unitOfWorkMock;

    public DeleteItemHandlerTests()
    {
        var mocker = new AutoMocker();
        _unitOfWorkMock = mocker.GetMock<IUnitOfWork>();
        _repositoryMock = mocker.GetMock<IItemRepository>();
        _handler = mocker.CreateInstance<DeleteItemHandler>();
    }

    [Fact]
    public async Task ItShouldNotDeleteWhenEntityIsInvalid()
    {
        // Arrange
        Guid id = Guid.Empty;
        Item? item = null;
        Result resultExpected = Result.CreateFailure(RequestValidationErrors.EntityNotFoundToDelete);

        SetupFindAsync(id, item);

        // Act
        var result = await _handler.HandleAsync(id, CancellationToken.None);

        // Assert
        Assert.IsType<Result>(result);
        Assert.True(result.IsFailure);
        Assert.Single(result.Errors);
        Assert.Equal(RequestValidationErrors.EntityNotFoundToDelete.Code, result.Errors[0].Code);

        VerifyFindAsync(id, Times.Once);
        VerifyCommitAsync(Times.Never);
    }

    [Fact]
    public async Task ItShouldDeleteWhenEntityIsValid()
    {
        // Arrange
        FakerHelper faker = new FakerHelper();
        Guid id = Guid.NewGuid();
        Item item = new(id, faker.RandomString(5, 15));
        Result resultExpected = Result.CreateSuccess();

        SetupFindAsync(id, item);

        // Act
        var result = await _handler.HandleAsync(id, cancellationToken: CancellationToken.None);

        // Assert
        Assert.IsType<Result>(result);
        Assert.Equal(result.IsSuccess, result.IsSuccess);
        Assert.Empty(result.Errors);

        VerifyFindAsync(id, Times.Once);
        VerifyCommitAsync(Times.Once);
    }

    private void SetupFindAsync(Guid id, Item? item)
    {
        _repositoryMock
            .Setup(p => p.FindAsync(id, It.IsAny<CancellationToken>()))
            .ReturnsAsync(item);
    }

    private void VerifyFindAsync(Guid id, Func<Times> times)
    {
        _repositoryMock.Verify(p => p.FindAsync(id, It.IsAny<CancellationToken>()), times);
    }

    private void VerifyCommitAsync(Func<Times> times)
    {
        _unitOfWorkMock.Verify(p => p.CommitAsync(It.IsAny<CancellationToken>()), times);
    }
}