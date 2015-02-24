package com.blogspot.androidgaidamak.acustomgooglesearchclient;

/**
 * Created by gaidamak on 21.02.15.
 */
public final class Protocol {
    public class SearchResponse {
        public SearchItem[] items;
        public Queries queries;
        public class SearchItem {
            public String kind;
            public String title;
            public String htmlTitle;
            public String link;
            public String displayLink;
            public String snippet;
            public String htmlSnippet;
            public String mime;
            public SearchImage image;

            public class SearchImage {
                public String contextLink;
                public int height;
                public int width;
                public int byteSize;
                public String thumbnailLink;
                public int thumbnailHeight;
                public int thumbnailWidth;
            }
        }

        public class Queries {
            public NextPageData[] nextPage;

            public class NextPageData {
                public int startIndex;
                public String searchTerms;
            }
        }
    }
}
