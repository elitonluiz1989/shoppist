using Domain.Entities;

namespace Application.Features.Items.CreateItem;

public static class CreateItemMap
{
    public static Item ToItem(this CreateItemRequest request) => new Item(request.Title);

    public static CreateItemResponse ToCreateItemResponse(this Item item) =>
        new CreateItemResponse(item.Id, item.Title, item.CreatedAt, item.UpdatedAt);
}