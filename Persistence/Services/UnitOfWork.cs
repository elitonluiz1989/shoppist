using Domain.Interfaces.Base;
using Persistence.Contexts;

namespace Persistence.Services;

public sealed class UnitOfWork(AppDbContext context) : IUnitOfWork
{
    private readonly AppDbContext _context = context;

    public async Task CommitAsync(CancellationToken cancellationToken)
    {
        await _context.SaveChangesAsync(cancellationToken);
    }
}