using Application.Items.Shared;
using Application.Shared.Results;
using Moq.AutoMock;
using Tests.Helpers;

namespace Tests.Application.Items.Shared;

public abstract class ItemValidatorTests<TItemValidator, TRequest>
    where TItemValidator : ItemValidator<TRequest>
    where TRequest : ItemRequest
{
    protected readonly FakerHelper Faker;
    protected readonly TItemValidator Validator;

    protected ItemValidatorTests()
    {
        var mocker = new AutoMocker();
        Faker = new FakerHelper();
        Validator = mocker.CreateInstance<TItemValidator>();
    }

    [Fact]
    public void ItShouldFailIfTitleIsEmpty()
    {
        // Arrange
        var request = new ItemRequest(Title: string.Empty) as TRequest;

        // Act
        Result<ItemResponse?> result = Validator.ValidateRequest(request);

        // Assert
        Assert.True(result.IsFailure);
        Assert.Single(result.Errors);
    }

    [Fact]
    public void ItShouldFailIfTitleLenghtIsGreaterThanMaxLength()
    {
        // Arrange
        var request = new ItemRequest(Faker.RandomString(101, 500)) as TRequest;

        // Act
        Result<ItemResponse?> result = Validator.ValidateRequest(request);

        // Assert
        Assert.True(result.IsFailure);
        Assert.Single(result.Errors);
    }
}