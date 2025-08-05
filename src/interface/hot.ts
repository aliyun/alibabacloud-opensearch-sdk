export interface HotQuery {
  hit?: number;
  user_id?: string;
  sort_type?: string;
  model_name?: string;
}

export interface HotResponse {
  request_id: string;
  searchtime: number;
  result: Array<{ pv: number, hot: string }>;
}
