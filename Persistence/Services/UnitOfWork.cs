using Domain.Interfaces.Base;
using Persistence.Contexts;

namespace Persistence.Services;

public sealed class UnitOfWork(AppDbContext context) : IUnitOfWork
{
    public async Task CommitAsync(CancellationToken cancellationToken)
    {
        await context.SaveChangesAsync(cancellationToken);
    }
}