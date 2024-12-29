using Application.Shared.Results;
using Application.Shared.Validators;
using Domain.Entities.Base;
using Domain.Interfaces.Base;

namespace Application.Shared.Handlers;

public abstract class CreateHandler<TEntity, TRequest, TResponse>(
    IUnitOfWork unitOfWork,
    IRequestValidator<TRequest, TResponse> validator,
    IRepository<TEntity> repository
)
    : Handler<TEntity, TRequest, TResponse>(validator)
    where TEntity : Entity
{
    protected override async Task<Result<TEntity?>> ExecuteAsync(TRequest request, CancellationToken cancellationToken)
    {
        TEntity entity = CreateEntity(request);

        repository.Create(entity);

        await unitOfWork.CommitAsync(cancellationToken);

        return Result<TEntity?>.CreateSuccess(entity);
    }

    protected abstract TEntity CreateEntity(TRequest request);
}