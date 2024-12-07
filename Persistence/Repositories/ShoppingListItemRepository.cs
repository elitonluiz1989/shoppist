using Domain.Entities;
using Domain.Interfaces;
using Persistence.Contexts;
using Persistence.Repositories.Base;

namespace Persistence.Repositories;

public sealed class ShoppingListItemRepository(AppDbContext context) :
    Repository<ShoppingListItem>(context), IShoppingListItemRepository
{
}