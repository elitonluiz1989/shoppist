using Domain.Entities.Base;
using Persistence.Contexts;

namespace Persistence.Repositories.Base;

public abstract class RecordControlRepository<TEntity>(AppDbContext context) : Repository<TEntity>(context)
    where TEntity : RecordControlEntity
{
    public override void Create(TEntity entity)
    {
        entity.CreatedAt = DateTimeOffset.Now;

        base.Create(entity);
    }

    public override void Update(TEntity entity)
    {
        entity.UpdatedAt = DateTimeOffset.Now;

        base.Update(entity);
    }
}