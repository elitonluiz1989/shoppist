using Domain.Entities.Base;

namespace Domain.Entities;

public sealed class Item : RecordControlEntity
{
    public string Title { get; set; } = string.Empty;
}