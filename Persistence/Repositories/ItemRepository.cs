using Domain.Entities;
using Domain.Interfaces;
using Persistence.Contexts;
using Persistence.Repositories.Base;

namespace Persistence.Repositories;

public sealed class ItemRepository(AppDbContext context) : RecordControlRepository<Item>(context), IItemRepository
{
}