using Domain.Entities;
using Domain.Interfaces;
using Persistence.Contexts;
using Persistence.Repositories.Base;

namespace Persistence.Repositories;

public sealed class ShoppingListRepository(AppDbContext context) :
    RecordControlRepository<ShoppingList>(context), IShoppingListRepository
{
}