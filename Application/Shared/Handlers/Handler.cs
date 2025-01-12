using Application.Shared.Interfaces;
using Application.Shared.Results;
using Domain.Entities.Base;

namespace Application.Shared.Handlers;

public abstract class Handler<TEntity, TRequest, TResponse>(
    IRequestValidator<TRequest, TResponse> validator
)
    : IRequestHandler<TRequest, TResponse>
    where TEntity : Entity
{
    public virtual async Task<Result<TResponse?>> HandleAsync(TRequest? request, CancellationToken cancellationToken)
    {
        Result<TResponse?> result = validator.ValidateRequest(request);

        if (result.IsFailure || request is null)
            return result;

        Result<TEntity?> executeResult = await ExecuteAsync(request, cancellationToken);

        if (executeResult.IsFailure || executeResult.Value is null)
            return Result<TResponse?>.CreateFailure(executeResult.Errors);

        TResponse response = CreateResponse(executeResult.Value);

        return Result<TResponse?>.CreateSuccess(response);
    }

    protected abstract Task<Result<TEntity?>> ExecuteAsync(TRequest request, CancellationToken cancellationToken);
    protected abstract TResponse CreateResponse(TEntity entity);
}