using Domain.Interfaces;
using Domain.Interfaces.Base;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Persistence.Contexts;
using Persistence.Repositories;
using Persistence.Services;

namespace Persistence;

public static class ServiceCollectionExtensions
{
    public static IServiceCollection AddPersistence(this IServiceCollection services)
    {
        services.AddScoped<IUnitOfWork, UnitOfWork>();
        services.AddScoped<IItemRepository, ItemRepository>();
        services.AddScoped<IShoppingListItemRepository, ShoppingListItemRepository>();
        services.AddScoped<IShoppingListRepository, ShoppingListRepository>();

        return services;
    }

    public static IServiceCollection AddPersistenceSqliteDbContext(this IServiceCollection services,
        string connectionString)
    {
        services.AddDbContext<AppDbContext>(p => p.UseSqlite(connectionString));

        return services;
    }
}