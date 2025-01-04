using Application.Shared.Results;

namespace Application.Items.Shared;

public static class ItemErrors
{
    public static ErrorResult ItemNotFound(Guid id) =>
        new("Item.Update.ItemNotFound", $"Item of Id {id} not found");
}