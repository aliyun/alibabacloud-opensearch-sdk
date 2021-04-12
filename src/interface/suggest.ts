export interface  SuggestQuery {
  query: string;
  hit?: number;
}

export interface SuggestResponse {
  request_id?: string;
  searchtime?: number;
  suggestions?: Array<{ suggestion: string }>;
  errors?: Error[];
}
