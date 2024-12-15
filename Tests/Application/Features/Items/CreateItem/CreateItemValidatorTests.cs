using Application.Features.Items.CreateItem;
using Application.Shared.Results;
using Bogus;
using Moq.AutoMock;

namespace Tests.Application.Features.Items.CreateItem;

public class CreateItemValidatorTests
{
    private readonly Faker _faker;
    private readonly CreateItemValidator _validator;

    public CreateItemValidatorTests()
    {
        var mocker = new AutoMocker();
        _faker = new Faker();
        _validator = mocker.CreateInstance<CreateItemValidator>();
    }

    [Fact]
    public void ItShouldFailIfTitleIsEmpty()
    {
        // Arrange
        var request = new CreateItemRequest(Title: string.Empty);

        // act
        Result<CreateItemResponse> result = _validator.ValidateRequest(request);

        // Assert
        Assert.True(result.IsFailure);
        Assert.Single(result.Errors);
    }

    [Fact]
    public void ItShouldFailIfTitleLenghtIsGreaterThanMaxLength()
    {
        // Arrange
        var request = new CreateItemRequest(_faker.Random.String(101));

        // act
        Result<CreateItemResponse> result = _validator.ValidateRequest(request);

        // Assert
        Assert.True(result.IsFailure);
        Assert.Single(result.Errors);
    }

    [Fact]
    public void ItShouldSuccessIfTitleIsValid()
    {
        // Arrange
        short randomLength = _faker.Random.Short(1, 100);
        string title = _faker.Random.String(randomLength);
        var request = new CreateItemRequest(title);

        // act
        Result<CreateItemResponse> result = _validator.ValidateRequest(request);

        // Assert
        Assert.True(result.IsSuccess);
        Assert.Empty(result.Errors);
    }
}