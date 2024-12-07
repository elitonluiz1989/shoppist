using Domain.Entities.Base;

namespace Domain.Entities;

public sealed class Item : RecordControlEntity
{
    public string Description { get; set; } = string.Empty;
    public bool Automatic { get; set; }
}