using Microsoft.AspNetCore.Http;
using System.Threading.Tasks;

namespace words_aspnet
{
    public class HostNameHeaderMiddleware
    {
        private readonly RequestDelegate _next;

        public HostNameHeaderMiddleware(RequestDelegate next)
        {
            _next = next;
        }

        public async Task Invoke(HttpContext context)
        {
            context.Response.Headers.Add("X-WordsAspnet-Host", System.Environment.MachineName);
            await _next(context);
        }
    }
}