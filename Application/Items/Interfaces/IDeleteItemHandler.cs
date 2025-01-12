using Application.Shared.Results;

namespace Application.Items.Interfaces;

public interface IDeleteItemHandler
{
    Task<Result> HandleAsync(Guid id, CancellationToken cancellationToken);
}