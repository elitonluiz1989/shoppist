using Application.Items.Interfaces;
using Application.Shared.Results;
using Application.Shared.Validators;
using Domain.Entities;
using Domain.Interfaces;
using Domain.Interfaces.Base;

namespace Application.Items.Features.Delete;

public class DeleteItemHandler(IUnitOfWork unitOfWork, IItemRepository repository) : IDeleteItemHandler
{
    public async Task<Result> HandleAsync(Guid id, CancellationToken cancellationToken)
    {
        Item? entity = await repository.FindAsync(id, cancellationToken);

        if (entity is null)
            return Result.CreateFailure(RequestValidationErrors.EntityNotFoundToDelete);

        repository.Delete(entity);

        await unitOfWork.CommitAsync(cancellationToken);

        return Result.CreateSuccess();
    }
}