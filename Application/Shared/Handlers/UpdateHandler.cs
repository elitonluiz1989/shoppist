using Application.Shared.Results;
using Application.Shared.Validators;
using Domain.Entities.Base;
using Domain.Interfaces.Base;

namespace Application.Shared.Handlers;

public abstract class UpdateHandler<TEntity, TRequest, TResponse>(
    IUnitOfWork unitOfWork,
    IRequestValidator<TRequest, TResponse> validator,
    IRepository<TEntity> repository
)
    : Handler<TEntity, TRequest, TResponse>(validator)
    where TEntity : Entity
{
    protected override async Task<Result<TEntity?>> ExecuteAsync(TRequest request, CancellationToken cancellationToken)
    {
        Result<TEntity?> entityResult = await GetEntityUpdated(request, cancellationToken);

        if (entityResult.IsFailure || entityResult.Value is null)
            return entityResult;

        repository.Update(entityResult.Value);

        await unitOfWork.CommitAsync(cancellationToken);

        return Result<TEntity?>.CreateSuccess(entityResult.Value);
    }

    protected abstract Task<Result<TEntity?>> GetEntityUpdated(TRequest request, CancellationToken cancellationToken);
}