using Application.Shared.Interfaces;
using Application.Shared.Requests;
using Application.Shared.Results;
using Domain.Entities.Base;

namespace Application.Shared.Handlers;

public abstract class Handler<TEntity, TRequest, TResponse> : IRequestHandler<TRequest, TResponse>
    where TEntity : Entity
{
    public virtual async Task<Result<TResponse>> HandleAsync(TRequest? request, CancellationToken cancellationToken)
    {
        if (request is null)
            return Result<TResponse>.CreateFailure(RequestValidationErrors.RequestIsNull);

        var result = Validate(request);

        if (result.IsFailure)
            return result;

        var entity = CreateEntity(request);

        await ExecuteAsync(entity, cancellationToken);

        var response = CreateResponse(entity);

        return Result<TResponse>.CreateSuccess(response);
    }

    protected abstract Result<TResponse> Validate(TRequest request);
    protected abstract TEntity CreateEntity(TRequest request);
    protected abstract Task ExecuteAsync(TEntity entity, CancellationToken cancellationToken);
    protected abstract TResponse CreateResponse(TEntity entity);
}