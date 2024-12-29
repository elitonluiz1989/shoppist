using Domain.Entities.Base;

namespace Domain.Entities;

public sealed class Item : RecordControlEntity
{
    public Item(Guid id, string title)
    {
        Id = id;
        Title = title;
    }

    public Item(string title) : this(Guid.NewGuid(), title)
    {
    }

    public Item() : this(string.Empty)
    {
    }

    public string Title { get; set; }
}