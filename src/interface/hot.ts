export interface HotQuery {
  hit?: number;
  userId?: string;
  sortType?: string;
  modelName?: string;
}

export interface HotResponse {
  request_id: string;
  searchtime: number;
  result: Array<{ pv: number, hot: string }>;
}
