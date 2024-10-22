import { Response } from "./base";

export interface CollectionFields {
  imei?: string;
  user_id?: string;
  biz_id: string;
  trace_id?: string;
  trace_info: string;
  rn: string;
  bhv_type: "expose" | "stay" | "click" | "cart" | "buy" | "collect" | "like" | "dislike" | "comment" | "share" | "subscribe" | "gift" | "download" | "read" | "tip" | "complain";
  bhv_time: string;
  bhv_detail: string;
  item_id: string;
  item_type: "goods" | "article" | "ask" | "bbs" | "download" | "image" | "media" | "recipe" | "news" | "institution" | "other";
  ip?: string;
  longitude?: string;
  latitude?: string;
  session_id?: string;
  spm?: string;
  report_src?: string;
  mac?: string;
  brand?: string;
  device_model?: string;
  resolution?: string;
  carrier?: string;
  access?: string;
  access_subtype?: string;
  os?: string;
  os_version?: string;
  language?: string;
  phone_md5?: string;
  reserve1?: string;
  reserve2?: string;
  reach_time?: string; // 格式yyyyMMddHHmmss
}

export type CollectionType = "USER" | "ITEM_INFO" | "BEHAVIOR" | "INDUSTRY_SPECIFIC";

export type CollectionRes = Required<Response> & { result: boolean; }
