using Application.Items.Shared;
using Application.Shared.Interfaces;
using Application.Shared.Results;
using Application.Shared.Validators;
using Domain.Interfaces;
using Domain.Interfaces.Base;
using Moq;
using Moq.AutoMock;

namespace Tests.Application.Items.Shared;

public abstract class ItemHandlerTests<THandler, TValidator, TRequest>
    where THandler : class, IRequestHandler<TRequest, ItemResponse>
    where TValidator : class, IRequestValidator<TRequest, ItemResponse>
    where TRequest : ItemRequest
{
    private readonly Mock<IUnitOfWork> _unitOfWorkMock;
    private readonly Mock<TValidator> _validatorMock;
    protected readonly THandler Handler;
    protected readonly Mock<IItemRepository> RepositoryMock;

    protected ItemHandlerTests()
    {
        var mocker = new AutoMocker();
        _unitOfWorkMock = mocker.GetMock<IUnitOfWork>();
        RepositoryMock = mocker.GetMock<IItemRepository>();
        _validatorMock = mocker.GetMock<TValidator>();
        Handler = mocker.CreateInstance<THandler>();
    }

    protected async Task ShouldItFailWhenRequestIsNotValidBase(TRequest invalidRequest)
    {
        // Arrange
        CancellationToken cancellationToken = CancellationToken.None;
        const string errorMessage = "It is a error";

        SetupValidatorResult(errorMessage);

        // Act
        Result<ItemResponse?> result = await Handler.HandleAsync(invalidRequest, cancellationToken);

        // Assert
        Assert.NotNull(result);
        Assert.True(result.IsFailure);
        Assert.NotEmpty(result.Errors);
        Assert.Single(result.Errors);
        Assert.Equal(errorMessage, result.Errors[0].Description);

        VerifyValidatorExecution(Times.Once);
        VerifyCommitExecution(Times.Never);
    }

    protected void SetupValidatorResult(string? errorMessage = null)
    {
        Result<ItemResponse?> result = string.IsNullOrWhiteSpace(errorMessage)
            ? Result<ItemResponse?>.CreateSuccess()
            : Result<ItemResponse?>.CreateFailure(
                new ErrorResult("error", errorMessage)
            );

        _validatorMock
            .Setup(x => x.ValidateRequest(It.IsAny<TRequest>()))
            .Returns(result);
    }

    protected void VerifyValidatorExecution(Func<Times> times)
    {
        _validatorMock.Verify(
            p => p.ValidateRequest(It.IsAny<TRequest>()),
            times
        );
    }

    protected void VerifyCommitExecution(Func<Times> times)
    {
        _unitOfWorkMock.Verify(p => p.CommitAsync(It.IsAny<CancellationToken>()), times);
    }
}