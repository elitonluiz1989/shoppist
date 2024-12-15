using Application.Features.Items.CreateItem;
using Application.Features.Items.CreateItem.Interfaces;
using Application.Shared.Results;
using Application.Shared.Validators;
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
    private readonly Mock<ICreateItemValidator> _validatorMock;

    public CreateItemHandlerTests()
    {
        var mocker = new AutoMocker();
        _unitOfWorkMock = mocker.GetMock<IUnitOfWork>();
        _repositoryMock = mocker.GetMock<IItemRepository>();
        _validatorMock = mocker.GetMock<ICreateItemValidator>();
        _handler = mocker.CreateInstance<CreateItemHandler>();
    }

    [Fact]
    public async Task ShouldItFailWhenRequestIsNull()
    {
        // Arrange
        CreateItemRequest? request = null;
        var cancellationToken = CancellationToken.None;

        // Act
        Result<CreateItemResponse> result = await _handler.HandleAsync(request, cancellationToken);

        // Assert
        Assert.NotNull(result);
        Assert.True(result.IsFailure);
        Assert.NotEmpty(result.Errors);
        Assert.Single(result.Errors);
        Assert.Equal(RequestValidationErrors.RequestIsNull, result.Errors[0]);

        VerifyValidatorExecution(Times.Never);
        VerifyServiceExecution(Times.Never);
    }

    [Fact]
    public async Task ShouldItFailWhenRequestIsNotValid()
    {
        // Arrange
        CreateItemRequest request = new(string.Empty);
        CancellationToken cancellationToken = CancellationToken.None;
        const string errorMessage = "It is a error";

        SetupValidatorResult(errorMessage);

        // Act
        Result<CreateItemResponse> result = await _handler.HandleAsync(request, cancellationToken);

        // Assert
        Assert.NotNull(result);
        Assert.True(result.IsFailure);
        Assert.NotEmpty(result.Errors);
        Assert.Single(result.Errors);
        Assert.Equal(errorMessage, result.Errors[0].Description);

        VerifyValidatorExecution(Times.Once);
        VerifyServiceExecution(Times.Never);
    }

    [Fact]
    public async Task ShouldItCreateItemWhenRequestIsValid()
    {
        // Arrange
        CreateItemRequest request = new("Item 001");
        CancellationToken cancellationToken = CancellationToken.None;

        SetupValidatorResult();

        // Act
        Result<CreateItemResponse> result = await _handler.HandleAsync(request, cancellationToken);

        // Assert
        Assert.NotNull(result);
        Assert.True(result.IsSuccess);
        Assert.Empty(result.Errors);
        Assert.NotNull(result.Value);
        CreateItemResponse response = Assert.IsType<CreateItemResponse>(result.Value);
        Assert.Equal(request.Title, response.Title);

        VerifyValidatorExecution(Times.Once);
        VerifyServiceExecution(Times.Once);
    }

    private void SetupValidatorResult(string? errorMessage = null)
    {
        Result<CreateItemResponse> result = string.IsNullOrWhiteSpace(errorMessage)
            ? Result<CreateItemResponse>.CreateSuccess()
            : Result<CreateItemResponse>.CreateFailure(
                new ErrorResult("error", errorMessage)
            );

        _validatorMock
            .Setup(x => x.ValidateRequest(It.IsAny<CreateItemRequest>()))
            .Returns(result);
    }

    private void VerifyValidatorExecution(Func<Times> times)
    {
        _validatorMock.Verify(
            p => p.ValidateRequest(It.IsAny<CreateItemRequest>()),
            times
        );
    }

    private void VerifyServiceExecution(Func<Times> times)
    {
        _repositoryMock.Verify(p => p.Create(It.IsAny<Item>()), times);
        _unitOfWorkMock.Verify(p => p.CommitAsync(It.IsAny<CancellationToken>()), times);
    }
}