#  Demo App

## Mô tả

Ứng dụng Android demo hiển thị tin tức với các tính năng:

- Màn hình Newsfeed hiển thị danh sách tin tức
- Màn hình Account cho phép chụp/chọn ảnh đại diện
- Màn hình Detail hiển thị chi tiết tin tức
- Hệ thống cache tối ưu hiệu suất

## Kiến trúc

- **MVVM Pattern**: Sử dụng ViewModel và LiveData
- **Repository Pattern**: Tách biệt logic data và UI
- **Navigation Component**: Điều hướng giữa các màn hình
- **Base Module**: Sử dụng BaseActivity, BaseFragment, BaseViewModel từ module base
- **Caching**: Cache dữ liệu và hình ảnh để tối ưu hiệu suất

### 1. Màn hình Newsfeed

- Load dữ liệu từ API: `https://raw.githubusercontent.com/Akaizz/static/master/newsfeed.json`
- Hiển thị danh sách tin tức với RecyclerView
- Click vào item để xem chi tiết

### 2. Màn hình Account

- Chụp ảnh từ camera
- Chọn ảnh từ gallery
- Lưu ảnh vào bộ nhớ dùng chung
- Hiển thị thông tin tài khoản

### 3. Màn hình Detail

- Load dữ liệu từ API: `https://raw.githubusercontent.com/Akaizz/static/master/detail.json`
- Hiển thị chi tiết tin tức
- Navigation back về màn hình trước

## Tối ưu hiệu suất (Bài 2)

### 1. Data Caching

- **NewsCache**: Cache dữ liệu JSON vào disk
- Cache validation với thời gian hết hạn (5 phút)

### 3. Resource Optimization

- Lazy loading cho RecyclerView

## Công nghệ sử dụng

- **Kotlin**: Ngôn ngữ lập trình chính
- **Hilt**: Dependency injection framework
- **Retrofit**: HTTP client cho API calls
- **Glide**: Image loading và caching với tối ưu hiệu suất
- **ViewBinding**: Type-safe view references
- **Coroutines**: Asynchronous programming
- **LiveData + State**: Reactive data streams với state management
- **MVVM**: Architecture pattern với Hilt injection

## Cài đặt

1. Clone repository
2. Mở project trong Android Studio
3. Sync Gradle files
4. Build và run trên device/emulator

## Permissions

- `INTERNET`: Truy cập API
- `CAMERA`: Chụp ảnh
- `READ_EXTERNAL_STORAGE`: Đọc ảnh từ gallery
- 'READ_MEDIA_IMAGES' : Đọc ảnh từ gallery (Android 13+)

## API Endpoints

- Newsfeed: `https://raw.githubusercontent.com/Akaizz/static/master/newsfeed.json`
- Details: `https://raw.githubusercontent.com/Akaizz/static/master/detail.json`

# Giải pháp hiển thị Newsfeed phức tạp trong RecyclerView

## 1. Phân loại item theo ViewType
Mỗi loại bài viết trong newsfeed được ánh xạ sang một `viewType` riêng:
- **Text Post**: chỉ có nội dung text (có thể chứa hashtag, link, mention).
- **Image Post**: 1 ảnh hoặc nhiều ảnh (album grid).
- **Video Post**: thumbnail + auto play khi scroll tới.
- **Ads Post**: bài quảng cáo với layout riêng.
- **Carousel Post**: xuất hiện sau mỗi 10–20 bài, hiển thị dạng slide ngang (nested RecyclerView).
- **Special Post**: sponsored, event, poll… (tùy nghiệp vụ).

---

## 2. Xử lý nội dung phức tạp trong Content
- **Hashtag & Mention**: dùng `SpannableString` để highlight + clickable span  
  → Click hashtag mở search, click mention mở profile.
- **Link Preview**: parse URL → hiển thị card preview (ảnh, tiêu đề, domain).
- **Album Ảnh**: hiển thị grid hoặc nested horizontal RecyclerView.
- **Auto Play Video**: kết hợp `RecyclerView.OnScrollListener` + `ExoPlayer`  
  → Tự động phát khi item vào viewport, dừng khi ra ngoài.

---

## 3. Tối ưu hiển thị nhiều loại dữ liệu
- **Nested RecyclerView**: dùng cho các item dạng carousel/slide ngang.
- **RecycledViewPool**: tái sử dụng ViewHolder giữa các RecyclerView con → giảm lag.
- **ListAdapter + DiffUtil**: cập nhật dữ liệu hiệu quả, tránh reload toàn bộ.

---

## 4. Quản lý luồng dữ liệu
- **Data model**: sử dụng `sealed class` hoặc field `type` để map sang viewType.
- **Backend API**: JSON trả về có field `"type": "text" | "image" | "video" | "carousel" | ...`.
- **Mapping**: app parse `type` → ánh xạ sang model tương ứng → render đúng ViewHolder.

---

## 5. Kết luận
Giải pháp này giúp:
- Linh hoạt hiển thị nhiều loại bài viết khác nhau trong cùng một newsfeed.
- Dễ mở rộng khi thêm loại item mới.
- Đảm bảo hiệu năng với danh sách dài nhờ pagination, recycled pool và diff util.
