using System;
using System.Collections.Generic;
using AlibabaCloud.SDK.OpenSearch;
using AlibabaCloud.SDK.OpenSearch.Models;
using Tea;
using Xunit;

namespace tests
{
    public class Test
    {
        [Fact]
        public void Test_Search()
        {
            string url = Environment.GetEnvironmentVariable("OpenSearch_Url");
            string id = Environment.GetEnvironmentVariable("OpenSearch_Id");
            string secret = Environment.GetEnvironmentVariable("OpenSearch_Secret");
            string appId = Environment.GetEnvironmentVariable("OpenSearch_AppId");
            string q = "query=desc:'keywords'&&config=format:fulljson,start:0,hit:500";

            //init config
            var config = new Config
            {
                Endpoint = url,
                AccessKeyId = id,
                AccessKeySecret = secret
            };

            //init client
            var client = new Client(config);
            var searchResult = new SearchRequestModel();
            var queryString = new SearchQuery();
            queryString.Query = q;
            searchResult.Headers = new Dictionary<string, string>();
            searchResult.Query = queryString;

            var response = client.Search(appId, searchResult);
            Assert.Equal("OK", response.Body.Status);

        }

    }
}
