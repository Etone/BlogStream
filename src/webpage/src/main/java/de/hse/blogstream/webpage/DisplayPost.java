package de.hse.blogstream.webpage;

public class DisplayPost {

        private String text;
        private String fromUser;
        private String url;

        public DisplayPost(String text, String fromUser, String url) {
            this.text = text;
            this.fromUser = fromUser;
            this.url = url;

        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getFromUser() {
            return fromUser;
        }

        public void setFromUser(String fromUser) {
            this.fromUser = fromUser;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
}
