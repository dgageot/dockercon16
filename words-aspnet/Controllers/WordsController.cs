using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace words_aspnet.Controllers
{
    [Route("api/[controller]")]
    public class WordsController : Controller
    {
        private readonly IWordsProvider _wordsProvider = new WordsProvider();

        public WordsController(IWordsProvider wordsProvider)
        {
            _wordsProvider = wordsProvider;
        }

        // GET words/noun
        [HttpPost("noun")]
        public string Noun()
        {
            return _wordsProvider.Noun;
        }

        // GET words/noun
        [HttpPost("adjective")]
        public string Adjective()
        {
            return _wordsProvider.Adjective;
        }

        // GET words/noun
        [HttpPost("verb")]
        public string Verb()
        {
            return _wordsProvider.Verb;
        }


    }
}
