using Application.Shared.Results;

namespace Application.Shared.Interfaces;

public interface IRequestHandler<TRequest, TResponse>
{
    Task<Result<TResponse?>> HandleAsync(TRequest? request, CancellationToken cancellationToken);
}