using Domain.Entities.Base;

namespace Domain.Interfaces.Base;

public interface IRepository<TEntity> where TEntity : Entity
{
    Task<List<TEntity>> AllAsync(CancellationToken cancellationToken);
    Task<TEntity?> FindAsync(Guid id, CancellationToken cancellationToken);
    void Create(TEntity entity);
    void Update(TEntity entity);
    void Delete(TEntity entity);
}