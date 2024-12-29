using Domain.Entities.Base;
using Domain.Interfaces.Base;
using Microsoft.EntityFrameworkCore;
using Persistence.Contexts;

namespace Persistence.Repositories.Base;

public abstract class Repository<TEntity>(AppDbContext context) : IRepository<TEntity> where TEntity : Entity
{
    public async Task<List<TEntity>> AllAsync(CancellationToken cancellationToken)
    {
        return await GetDbSet().ToListAsync(cancellationToken);
    }

    public async Task<TEntity?> FindAsync(Guid id, CancellationToken cancellationToken)
    {
        return await GetDbSet().FindAsync([id, cancellationToken], cancellationToken: cancellationToken);
    }

    public virtual void Create(TEntity entity)
    {
        GetDbSet().Add(entity);
    }

    public virtual void Update(TEntity entity)
    {
        GetDbSet().Update(entity);
    }

    public void Delete(TEntity entity)
    {
        GetDbSet().Remove(entity);
    }

    private DbSet<TEntity> GetDbSet()
    {
        return context.Set<TEntity>();
    }
}