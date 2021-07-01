# SE114-Note-App
![banner_0](https://user-images.githubusercontent.com/62055305/123108672-d036c300-d464-11eb-887a-abccf8068ebe.png)
## Giới thiệu
### Tên đề tài 
Ứng dụng ghi chú EasyNote\
![image](https://user-images.githubusercontent.com/62055305/122946806-de70da80-d3a3-11eb-9b47-0b9b41b901ed.png)
### Thông tin nhóm
* Hồ Hữu Thịnh-19522275 
* Trần Thành Trung-19522436 
* Nguyễn Minh Thiện-19522262
## Mô tả
### Mô tả tổng thể 
![Mô tả tổng thể Note App SE114](https://user-images.githubusercontent.com/62055305/123195416-576e5000-d4d2-11eb-826b-f232c5e08dc2.png)
### Mô tả người dùng
![Use case](https://user-images.githubusercontent.com/62055305/123274017-56b6d780-d52d-11eb-8ef5-6b769f488ece.png)
## Thiết kế
### Thiết kế dữ liệu
Hệ quản trị cơ sở dữ liệu: SQLite thông qua Room Database
![ERD Note App](https://user-images.githubusercontent.com/62055305/123108031-4129ab00-d464-11eb-9b12-b72a4693438b.png)
### Thiết kế giao diện
#### Sơ đồ liên kết các màn hình
![Mô hình liên kết các màn hình](https://user-images.githubusercontent.com/62055305/123029216-fa5b9700-d40a-11eb-811b-5224de121f3c.png)
#### Danh sách các màn hình
##### Màn hình chính
![main light](https://user-images.githubusercontent.com/62055305/123032297-e8302780-d40f-11eb-82c6-a1505f0816e4.JPG)
![main dark](https://user-images.githubusercontent.com/62055305/123032302-ea928180-d40f-11eb-8233-07542783c9cb.JPG)
##### Màn hình tìm kiếm
![Search Note Screen](https://user-images.githubusercontent.com/62055305/123032317-f2eabc80-d40f-11eb-855d-d7f363a06dbf.JPG)
##### Màn hình Navigation
![navigate screen](https://user-images.githubusercontent.com/62055305/124143188-1c14e800-dab5-11eb-8124-4e3f04b801c4.JPG)
![navigate screen dark](https://user-images.githubusercontent.com/62055305/124143196-1d461500-dab5-11eb-9fec-dab38fbb1401.JPG)
##### Màn hình Note và các customization của note
![Normal note](https://user-images.githubusercontent.com/62055305/123032588-5f65bb80-d410-11eb-956c-47fb254e32d6.JPG)
![Note Bottomsheet](https://user-images.githubusercontent.com/62055305/123032372-072eb980-d410-11eb-8169-1e6df86cac51.JPG) \
![Note info with checbox](https://user-images.githubusercontent.com/62055305/123032390-10b82180-d410-11eb-9ff6-ed0798dba410.JPG)
![note info with no checkbox](https://user-images.githubusercontent.com/62055305/123032656-815f3e00-d410-11eb-914e-a801e697347c.JPG)
##### Màn hình Notebook
![notebook screen](https://user-images.githubusercontent.com/62055305/123032673-8e7c2d00-d410-11eb-9d8d-736b0650401b.JPG)
![Adding note to notebook](https://user-images.githubusercontent.com/62055305/123032838-e4e96b80-d410-11eb-8346-cefc674f8f00.JPG)
![Inside 1 Notebook](https://user-images.githubusercontent.com/62055305/123032774-bf5c6200-d410-11eb-999a-cea2054f3029.JPG)
##### Màn hình Bookmark
![Bookmark screen](https://user-images.githubusercontent.com/62055305/123032699-9b991c00-d410-11eb-9425-1f3324332e3d.JPG)
##### Màn hình Reminder
![Reminder light](https://user-images.githubusercontent.com/62055305/124143074-03a4cd80-dab5-11eb-8000-83ad6a83d04f.JPG)
![Reminder dark](https://user-images.githubusercontent.com/62055305/124143416-4c5c8680-dab5-11eb-8cf6-951c4ae84569.JPG)
##### Màn hình Setting
![Setting screen](https://user-images.githubusercontent.com/62055305/124143044-fd165600-dab4-11eb-8bdb-bca82c39b9bc.JPG)
![Setting screen dark](https://user-images.githubusercontent.com/62055305/124143052-fee01980-dab4-11eb-88f3-1df6b9c6bb15.JPG)
## Môi trường phát triển và triển khai
### Môi trường phát triển
* Hệ điều hành: Microsoft Windows 10
* Hệ quản trị cơ sở dữ liệu: SQLite thông qua Room database
* Công cụ xây dựng ứng dụng: Android Studio
### Môi trường triển khai
* Nền tảng: android
## Tài liệu tham khảo
* Java\
https://www.w3schools.com/java/
* Android Studio\
https://www.tutorialspoint.com/android/index.htm \
https://developer.android.com/guide
* Thư viện sử dụng\
https://github.com/chthai64/SwipeRevealLayout
