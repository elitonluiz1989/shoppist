using Domain.Entities.Base;

namespace Domain.Entities;

public sealed class ShoppingListItem : Entity
{
    public Guid ShoppingListId { get; set; }
    public Guid ItemId { get; set; }
    public int Quantity { get; set; }
    public decimal Price { get; set; }

    public Item? Item { get; set; }
}