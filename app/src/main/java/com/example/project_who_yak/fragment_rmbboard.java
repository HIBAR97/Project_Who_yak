package com.example.project_who_yak;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class fragment_rmbboard extends Fragment {

    public class list_item {
        //추가한 변수
        private int profile_image;
        private String nickname;
        private String title;
        private ContactsContract.Data write_date;
        private String content;

        public int getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(int profile_image) {
            this.profile_image = profile_image;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }



        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public ContactsContract.Data getWrite_date() {
            return write_date;
        }

        public void setWrite_date(ContactsContract.Data write_date) {
            this.write_date = write_date;
        }

        public list_item(int profile_image, String nickname, String title, ContactsContract.Data write_date, String content) {
            this.profile_image = profile_image;
            this.nickname = nickname;
            this.title = title;
            this.write_date = write_date;
            this.content = content;
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rmbboard, container, false);
    }
}