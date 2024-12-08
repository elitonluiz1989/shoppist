using Application.Features.Items.CreateItem;
using Microsoft.Extensions.DependencyInjection;

namespace Application;

public static class ServiceCollectionExtensions
{
    public static IServiceCollection AddApplication(this IServiceCollection services)
    {
        services.AddScoped<ICreateItemHandler, CreateItemHandler>();

        return services;
    }
}