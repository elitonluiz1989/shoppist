using Application.Features.Items.CreateItem;
using Application.Features.Items.Shared;
using Application.Shared.Results;
using Tests.Application.Features.Items.Shared;

namespace Tests.Application.Features.Items.CreateItem;

public class CreateItemValidatorTests : ItemValidatorTests<CreateItemValidator, ItemRequest>
{
    [Fact]
    public void ItShouldSuccessIfRequestIsValid()
    {
        // Arrange
        string title = Faker.RandomString(1, 100);
        var request = new ItemRequest(title);

        // Act
        Result<ItemResponse?> result = Validator.ValidateRequest(request);

        // Assert
        Assert.True(result.IsSuccess);
        Assert.Empty(result.Errors);
    }
}