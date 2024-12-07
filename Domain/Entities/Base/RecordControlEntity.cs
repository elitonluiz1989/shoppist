namespace Domain.Entities.Base;

public abstract class RecordControlEntity : Entity
{
    public DateTimeOffset CreatedAt { get; set; }
    public DateTimeOffset UpdatedAt { get; set; }
}