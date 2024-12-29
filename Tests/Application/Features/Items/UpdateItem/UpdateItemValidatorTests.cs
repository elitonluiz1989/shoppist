using Application.Features.Items.Shared;
using Application.Features.Items.UpdateItem;
using Application.Shared.Results;
using Tests.Application.Features.Items.Shared;

namespace Tests.Application.Features.Items.UpdateItem;

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