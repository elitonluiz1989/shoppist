using Application.Features.Items.CreateItem;
using Application.Shared.Requests;
using Domain.Entities;
using Domain.Interfaces;
using Domain.Interfaces.Base;
using Moq;
using Moq.AutoMock;

namespace Tests.Application.Features.Items.CreateItem;

public class CreateItemHandlerTests
{
    private readonly CreateItemHandler _handler;
    private readonly Mock<IItemRepository> _repositoryMock;
    private readonly Mock<IUnitOfWork> _unitOfWorkMock;

    public CreateItemHandlerTests()
    {
        var mocker = new AutoMocker();
        _unitOfWorkMock = mocker.GetMock<IUnitOfWork>();
        _repositoryMock = mocker.GetMock<IItemRepository>();
        _handler = mocker.CreateInstance<CreateItemHandler>();
    }

    [Fact]
    public async Task ShouldItFailWhenRequestIsNull()
    {
        // Arrange
        CreateItemRequest? request = null;
        var cancellationToken = CancellationToken.None;

        // Act
        var result = await _handler.HandleAsync(request, cancellationToken);

        // Assert
        Assert.NotNull(result);
        Assert.True(result.IsFailure);
        Assert.NotEmpty(result.Errors);
        Assert.Single(result.Errors);
        Assert.Equal(RequestValidationErrors.RequestIsNull, result.Errors[0]);

        VerifyServiceExecution(Times.Never);
    }

    [Fact]
    public async Task ShouldItFailWhenRequestIsNotValid()
    {
        // Arrange
        CreateItemRequest request = new(string.Empty);
        var cancellationToken = CancellationToken.None;

        // Act
        var result = await _handler.HandleAsync(request, cancellationToken);

        // Assert
        Assert.NotNull(result);
        Assert.True(result.IsFailure);
        Assert.NotEmpty(result.Errors);
        Assert.Single(result.Errors);
        Assert.NotEmpty(result.Errors[0].Description);

        VerifyServiceExecution(Times.Never);
    }

    [Fact]
    public async Task ShouldItCreateItemWhenRequestIsValid()
    {
        // Arrange
        CreateItemRequest request = new("Item 001");
        var cancellationToken = CancellationToken.None;

        // Act
        var result = await _handler.HandleAsync(request, cancellationToken);

        // Assert
        Assert.NotNull(result);
        Assert.True(result.IsSuccess);
        Assert.Empty(result.Errors);
        Assert.NotNull(result.Value);
        var response = Assert.IsType<CreateItemResponse>(result.Value);
        Assert.Equal(request.Title, response.Title);

        VerifyServiceExecution(Times.Once);
    }

    private void VerifyServiceExecution(Func<Times> times)
    {
        _repositoryMock.Verify(p => p.Create(It.IsAny<Item>()), times);
        _unitOfWorkMock.Verify(p => p.CommitAsync(It.IsAny<CancellationToken>()), times);
    }
}