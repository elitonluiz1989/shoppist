using Domain.Entities.Base;

namespace Domain.Entities;

public sealed class ShoppingList : RecordControlEntity
{
    public string? Name { get; set; }
}