using Application.Items.Features.Update;
using Application.Items.Shared;
using Application.Shared.Results;
using Tests.Application.Items.Shared;

namespace Tests.Application.Items.Features.Update;

public class UpdateItemValidatorTests : ItemValidatorTests<UpdateItemValidator, UpdateItemRequest>
{
    [Fact]
    public void ItShouldFailIfIdIsInvalid()
    {
        // Arrange
        var request = new UpdateItemRequest(Guid.Empty, Faker.Random.String(100));

        // act
        Result<ItemResponse?> result = Validator.ValidateRequest(request);

        // Assert
        Assert.True(result.IsFailure);
        Assert.Single(result.Errors);
    }

    [Fact]
    public void ItShouldSuccessIfRequestIsValid()
    {
        // Arrange
        var request = new UpdateItemRequest(Id: Guid.NewGuid(), Title: Faker.RandomString(1, 100));

        // Act
        Result<ItemResponse?> result = Validator.ValidateRequest(request);

        // Assert
        Assert.True(result.IsSuccess);
        Assert.Empty(result.Errors);
    }
}