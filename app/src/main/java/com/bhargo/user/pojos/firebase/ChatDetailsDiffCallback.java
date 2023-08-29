package com.bhargo.user.pojos.firebase;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

public class ChatDetailsDiffCallback extends DiffUtil.Callback {

            private final List<ChatDetails> mOldChatList;
        private final List<ChatDetails> mNewChatList;

        public ChatDetailsDiffCallback(List<ChatDetails> oldChatList, List<ChatDetails> newChatList) {
            this.mOldChatList = oldChatList;
            this.mNewChatList = newChatList;
        }

        @Override
        public int getOldListSize() {
            return mOldChatList.size();
        }

        @Override
        public int getNewListSize() {
            return mNewChatList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return mOldChatList.get(oldItemPosition).getMessageID() == mNewChatList.get(
                    newItemPosition).getMessageID();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            final ChatDetails oldChat = mOldChatList.get(oldItemPosition);
            final ChatDetails newChat = mNewChatList.get(newItemPosition);

            return oldChat.getFilePath().equals(newChat.getFilePath());
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            // Implement method if you're going to use ItemAnimator
            return super.getChangePayload(oldItemPosition, newItemPosition);
        }
    }
