using Domain.Entities.Base;

namespace Domain.Entities;

public sealed class Item : RecordControlEntity
{
    public Item(string title)
    {
        Title = title;
    }

    public Item() : this(string.Empty)
    {
    }

    public string Title { get; init; }
}