export interface SearchQuery {
  query: string;
  fetch_fields?: string[];
  qp?: string;
  disable?: string;
  firstRankName?: string;
  secondRankName?: string;
  user_id?: string;
  abtest?: string;
  category_prediction?: string;
  raw_query?: string;
  summary?: string;
  biz?: string;
  request_id?: string;
  config: {
    hit?: number;
    start?: number;
    format?: string;
  }
}

export interface SearchResponse {
  status?: string;
  request_id?: string;
  result?: SearchResult;
  errors?: Error[];
}

interface SearchResult {
  searchtime?: number;
  total?: number;
  viewtotal?: number;
  num?: number;
  items?: { [key: string]: any }[];
  facet?: SearchResultFacet[];
}

interface SearchResultFacet {
  key?: string;
  items?: Array<{ value?: string, count?: number }>;
}
