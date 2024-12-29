using Application.Features.Items.Shared;
using Application.Shared.Interfaces;

namespace Application.Features.Items.UpdateItem.Interfaces;

public interface IUpdateItemHandler : IRequestHandler<UpdateItemRequest, ItemResponse>
{
}