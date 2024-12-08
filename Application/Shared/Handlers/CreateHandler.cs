using Domain.Entities.Base;
using Domain.Interfaces.Base;

namespace Application.Shared.Handlers;

public abstract class CreateHandler<TEntity, TRequest, TResponse>(
    IUnitOfWork unitOfWork,
    IRepository<TEntity> repository
)
    : Handler<TEntity, TRequest, TResponse>
    where TEntity : Entity
{
    protected override async Task ExecuteAsync(TEntity entity, CancellationToken cancellationToken)
    {
        repository.Create(entity);

        await unitOfWork.CommitAsync(cancellationToken);
    }
}