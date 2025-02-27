export interface SuggestQuery {
  query: string;
  hit?: number;
  user_id?: string;
  re_search?: string;
}

export interface SuggestResponse {
  request_id?: string;
  searchtime?: number;
  suggestions?: Array<{ suggestion: string }>;
  errors?: Error[];
}
