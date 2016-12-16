using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;

namespace words_aspnet.Controllers
{
    [Route("")]
    public class WordsController : Controller
    {
        private readonly IWordsProvider _wordsProvider = new WordsProvider();

        public WordsController(IWordsProvider wordsProvider)
        {
            _wordsProvider = wordsProvider;
        }

        // GET words/noun
        [HttpGet("noun")]
        public string GetNoun()
        {
            return _wordsProvider.Noun;
        }

        // GET words/noun
        [HttpGet("adjective")]
        public string GetAdjective()
        {
            return _wordsProvider.Adjective;
        }

        // GET words/noun
        [HttpGet("verb")]
        public string GetVerb()
        {
            return _wordsProvider.Verb;
        }


    }
}
