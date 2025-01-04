using Domain.Entities;

namespace Application.Items.Shared;

public static class ItemMap
{
    public static Item ToItem(this ItemRequest request) => new(request.Title);

    public static ItemResponse ToItemResponse(this Item item) =>
        new(item.Id, item.Title, item.CreatedAt, item.UpdatedAt);
}